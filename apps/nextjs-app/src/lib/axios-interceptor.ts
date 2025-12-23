import axios from 'axios';
import { AuthControllerService, OpenAPI } from '@/lib/api';
import { logoutAction } from '@/app/actions/auth';

let isRefreshing = false;
let failedQueue: any[] = [];

const processQueue = (error: any, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });

  failedQueue = [];
};

export const setupAxiosInterceptors = () => {
  axios.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const originalRequest = error.config;

      // If error is 401 and not a retry
      if (error.response?.status === 401 && !originalRequest._retry) {
        
        // If the error comes from the login or refresh endpoint itself, don't retry
        if (originalRequest.url?.includes('/auth/login') || originalRequest.url?.includes('/auth/refresh')) {
            return Promise.reject(error);
        }

        if (isRefreshing) {
          return new Promise(function (resolve, reject) {
            failedQueue.push({ resolve, reject });
          })
            .then((token) => {
              originalRequest.headers['Authorization'] = 'Bearer ' + token;
              return axios(originalRequest);
            })
            .catch((err) => {
              return Promise.reject(err);
            });
        }

        originalRequest._retry = true;
        isRefreshing = true;

        try {
          // We need to get the refresh token from cookies.
          // Since this runs on client side, we can't access httpOnly cookies directly via JS.
          // However, the browser sends cookies automatically with the request.
          // So we call a Next.js Server Action or API Route that handles the refresh logic.
          
          // BUT: Our backend expects the refresh token in the body.
          // And we can't read the httpOnly cookie here to put it in the body.
          
          // SOLUTION: We need a Next.js API Route (Proxy) for refresh that reads the cookie and calls backend.
          // OR: We assume the backend can read the refresh token from cookie if we send it?
          // Looking at DefaultAuthService.java: refresh(RefreshRequest refreshRequest)
          // It expects RefreshRequest DTO which has 'refreshToken' field.
          
          // This is a problem. Client-side JS cannot read HttpOnly cookies.
          // So we cannot construct the RefreshRequest body.
          
          // We must use a Server Action to perform the refresh.
          // The Server Action can read the cookie, call the backend, and set the new cookie.
          
          const result = await refreshSessionAction();
          
          if (result.success && result.accessToken) {
             // Update OpenAPI token for subsequent requests
             OpenAPI.TOKEN = result.accessToken;
             
             // Update Authorization header for the retried request
             axios.defaults.headers.common['Authorization'] = 'Bearer ' + result.accessToken;
             originalRequest.headers['Authorization'] = 'Bearer ' + result.accessToken;
             
             processQueue(null, result.accessToken);
             return axios(originalRequest);
          } else {
             throw new Error("Refresh failed");
          }

        } catch (err) {
          processQueue(err, null);
          // Logout user if refresh fails
          await logoutAction();
          window.location.href = '/login';
          return Promise.reject(err);
        } finally {
          isRefreshing = false;
        }
      }

      return Promise.reject(error);
    }
  );
};

// We need a server action that handles the refresh logic securely
import { refreshSessionAction } from '@/app/actions/auth';
