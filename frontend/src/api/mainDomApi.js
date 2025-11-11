// api/mainDomApi.js
export async function getMainDom() {
  const res = await fetch("http://localhost:8080/api/maindom/");
  if (!res.ok) {
    console.error(res.status);
    throw new Error("Erreur API")
  };
  return res.json();
}

export async function getMainDomD3() {
  const res = await fetch("http://localhost:8080/api/maindom/D3");
  if (!res.ok) {
    console.error(res.status);
    throw new Error("Erreur API")
  };
  return res.json();
}