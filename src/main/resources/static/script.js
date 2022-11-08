const url = 'http://localhost:8080';
const itemEndpoint = url + '/itens';

//function loadItems(){
//
//    fetch(itemEndpoint)
//        .then((response) => response.json())
//        .then((data) => {
//
//
//
//
//        });
//}



function fazGet(url){
    let request = new XMLHttpRequest();
    request.open("GET", url, false);
    request.send();
    return request.responseText;
}

function criaLinha(item){
    linha = document.createElement("tr");
    tdIdentificador = document.createElement("td");
    tdModelo = document.createElement("td");
    tdFabricante = document.createElement("td");
    tdAnoDeFabricacao = document.createElement("td");
    tdDataDeInclusao = document.createElement("td");
    tdUltimaAtualizacao = document.createElement("td");

    tdIdentificador.innerHTML = item.identificador;
    tdModelo.innerHTML = item.modelo;
    tdFabricante.innerHTML = item.fabricante;
    tdAnoDeFabricacao.innerHTML = item.anoDeFabricacao;
    tdDataDeInclusao.innerHTML = item.dataDeInclusao;
    tdUltimaAtualizacao.innerHTML = item.ultimaAtualizacao;

    linha.appendChild(tdIdentificador);
    linha.appendChild(tdModelo);
    linha.appendChild(tdFabricante);
    linha.appendChild(tdAnoDeFabricacao);
    linha.appendChild(tdDataDeInclusao);
    linha.appendChild(tdUltimaAtualizacao);

    return linha;
}
function main(){

    let data = fazGet("/itens")
    let itens = JSON.parse(data)
    let tabela = document.getElementById('tbody')
    itens.forEach(element => {
        let linha = criaLinha(element);
        tabela.appendChild(linha)
    })
}

function sendData(){
    let model = document.querySelector('#modelo');
    let manufacturer = document.querySelector('#fabricante');
    let yearFabrication = document.querySelector('#anoDeFabricacao');

    let sendData = new XMLHttpRequest();

    sendData.open("POST", "itens", true);
    sendData.setRequestHeader("Content-type", "application/json");
    var data = JSON.stringify({"modelo": model.value, "fabricante": manufacturer.value, "anoDeFabricacao": yearFabrication.value});
    console.log(data);
    sendData.send(data);

}
