import type { Route } from "./+types/about";
import Navigation from "../components/Navigation";
import About from "../components/About";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "About - City List" },
    { name: "description", content: "About the City List application" },
  ];
}

export default function AboutRoute() {
  return (
    <>
      <Navigation />
      <About />
    </>
  );
}