import { NextRequest, NextResponse } from "next/server";
import { getFromLocalStorage } from "./utils/utils";

export function middleware(req) {
  let res = NextResponse.next();
  const accessToken = req.cookies.get("accessToken");
  const role = req.cookies.get("role");
  // Skip middleware for login page

  if (req.nextUrl.pathname.startsWith("/login")) {
    return NextResponse.next();
  }
  if (req.nextUrl.pathname.startsWith("/auth")) {
    return NextResponse.next();
  }
  if (!accessToken || !isValidAccessToken(accessToken)) {
    return NextResponse.redirect(new URL("/login", req.url));
  }
  if (req.nextUrl.pathname.startsWith("/admin")) {
    if (role.value !== "ROLE_ADMIN") {
      return NextResponse.redirect(new URL("/404", req.url));
    }
  }

  // Access token is present and valid, proceed with the request
  return res;
}
function isValidAccessToken(accessToken) {
  // Check if the access token is valid, e.g. by verifying its signature or checking its expiration date
  // Return true if the token is valid, false otherwise
  return true; // Replace this with your own implementation
}
export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
