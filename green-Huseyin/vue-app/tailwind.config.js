/** @type {import('tailwindcss').Config} */
module.exports = {
    darkMode: 'class',
    content: [
        './public/**/index.html',
        './src/**/*.{vue, js}'
    ],
    theme: {
        extend: {
            transitionProperty: {
                'height': 'height'
            },
            colors: {
                'text-green': '#023020'
            },
            screens: {
                'extra-sm': '500px'
            }
        },
    },
    plugins: [],
}