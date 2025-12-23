import { OpenAPI } from "@/lib/api/core/OpenAPI";

// Configure the OpenAPI client
OpenAPI.BASE = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";
OpenAPI.WITH_CREDENTIALS = true;
OpenAPI.CREDENTIALS = "include";
