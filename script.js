console.log("Hello from !nom!")
function getDoss(){
	
}
lstdom = ""
// prefere le gestionnaire vent
/*window.onload((e)=>{
	console.error(e)
})*/
window.addEventListener("load", (event) => {
	//log.textContent += "load\n";
	console.log("fsd")
	// pour le moment pas d'await mais il va falloir
	response =  fetch("./lstdom.txt").then((data)=>{
		dataTxt = data.text();
		lstdom = dataTxt
		console.log(lstdom)
		return lstdom;
	}).then((lst)=>{
		//console.log(lst)
		listeDomaine = lst.split("\r\n")
		console.log(listeDomaine)
		// pour chaque domaines, faire lien
		nav = document.getElementsByTagName("nav")[0]
		
		for (dom of listeDomaine){
			const li = document.createElement("li");
			const a = document.createElement("a");
			a.href = dom+"/";  // ou un chemin relatif selon contexte
			a.textContent = dom;
			//a.target = "_blank";  // ouvre dans un nouvel onglet (optionnel)
			li.appendChild(a);
			//navUl.appendChild(li);
			nav.appendChild(li)
		}
}).finally(()=>{
	console.log("fini")
})
	// il faut lresulat de la promise 
	//data = response.json()
	//console.error(response.resolve())	
});