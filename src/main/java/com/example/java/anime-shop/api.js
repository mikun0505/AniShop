import { API_BASE } from "../constants";

export const api = {
  get: (path, token) =>
    fetch(`${API_BASE}${path}`, {
      headers: token ? { Authorization: `Bearer ${token}` } : {},
    }).then((r) => r.json()),

  post: (path, body, token) =>
    fetch(`${API_BASE}${path}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
      },
      body: JSON.stringify(body),
    }).then((r) => r.json()),

  delete: (path, token) =>
    fetch(`${API_BASE}${path}`, {
      method: "DELETE",
      headers: token ? { Authorization: `Bearer ${token}` } : {},
    }).then((r) => r.json()),
};
