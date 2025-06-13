import type { Route } from "./+types/edit.$id";
import Navigation from "../components/Navigation";
import CityEdit from "../components/CityEdit";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Edit City - City List" },
    { name: "description", content: "Edit city information" },
  ];
}

export default function EditRoute() {
  return (
    <>
      <Navigation />
      <CityEdit />
    </>
  );
}