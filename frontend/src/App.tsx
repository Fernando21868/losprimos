"use client";
import { ThemeProvider } from "./@components/theme-provider";
import Router from "./@components/router/Routes";

function App() {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <Router></Router>
    </ThemeProvider>
  );
}

export default App;
