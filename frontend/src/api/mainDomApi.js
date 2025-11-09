// api/mainDomApi.js
export async function getMainDom() {
  const res = await fetch("http://localhost:8080/api/maindom");
  if (!res.ok) throw new Error("Erreur API");
  return res.json();
}
