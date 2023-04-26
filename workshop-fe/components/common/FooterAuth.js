import React, { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/router";
import { withErrorBoundary } from "react-error-boundary";
const FooterAuth = () => {
  return (
    <footer className="relative z-10">
      <div className=" bgPrimary w-full flex justify-center  text-sm items-center p-5 text-white mr-4   ">
        © 2023 Greenwich.edu.vn. All Right Reserved
      </div>
    </footer>
  );
};

function ErrorComponent({ error }) {
  return (
    <div className="p-4 text-red-500 border border-red-500">
      <span>{error.message}</span>
    </div>
  );
}

export default withErrorBoundary(FooterAuth, {
  FallbackComponent: ErrorComponent,
});
