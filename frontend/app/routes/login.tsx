import type { Route } from "./+types/login";
import Navigation from "../components/Navigation";
import Login from "../components/Login";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Login - City List" },
    { name: "description", content: "Login to manage cities" },
  ];
}

export default function LoginRoute() {
  return (
    <>
      <Navigation />
      <Login />
    </>
  );
}