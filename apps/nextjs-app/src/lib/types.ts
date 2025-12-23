import { JWTPayload } from "jose"

export interface CustomJwtPayload extends JWTPayload {
  email?: string
  firstName?: string
  lastName?: string
  roles?: string[]
}
