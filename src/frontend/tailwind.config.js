// eslint-disable-next-line no-undef
module.exports = {
  content: ["./src/**/*.{html,js,vue}", "./index.html"],
  theme: {
    extend: {
      transitionProperty: {
        height: "height",
      },
    },
  },
  // eslint-disable-next-line no-undef
  plugins: [require("daisyui"), require("prettier-plugin-tailwindcss")],
  daisyui: {
    themes: false,
  },
};
