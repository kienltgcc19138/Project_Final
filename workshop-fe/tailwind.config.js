/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./app/**/*.{js,ts,jsx,tsx}",
    "./pages/**/*.{js,ts,jsx,tsx}",
    "./components/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      extend: {
        colors: {
          textPrimary: "#777",
          bgPrimary: "#2d3791",
          bgGray: "#F5F5F5",
          hightOrange: "#EC903B",
          lightOrange: "#EFB57F",
          hightGreen: "#358238",
          lightGreen: "#7BC07E",
          lightWhite: "#F5F5F5",
          textBlack: "#121212",
          textGray: "#808080",
        },
        boxShadow: {
          lightShadow: "2px 2px 8px rgba(18, 18, 18, 0.08)",
          mediumShadow: "2px 2px 8px rgba(18, 18, 18, 0.24)",
          heightShadow: "8px 8px 32px rgba(18, 18, 18, 0.24)",
        },
        
      },
    },
  },
  plugins: [
    require('@tailwindcss/line-clamp'),
  ],
};
