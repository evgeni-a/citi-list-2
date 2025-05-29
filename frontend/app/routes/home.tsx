import type { Route } from "./+types/home";
import Navigation from "../components/Navigation";
import CityList from "../components/CityList";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "City List" },
    { name: "description", content: "Browse and manage cities" },
  ];
}

export default function Home() {
  return (
    <>
      <Navigation />
      <CityList />
    </>
  );
}
