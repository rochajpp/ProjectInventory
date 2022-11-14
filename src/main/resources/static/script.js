const url = 'http://localhost:8080';
const itemEndpoint = url + '/itens';

function fazGet(url){
    let request = new XMLHttpRequest();
    request.open("GET", url, false);
    request.send();
    return request.responseText;
}

function getData(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    var ident = urlParams.get('id');
    var mode = urlParams.get('mod');
    var fabr = urlParams.get('fab');
    var anoFab = urlParams.get('afab');
    var dataInc = urlParams.get('di');
    ident = ident - 1263292;
    var array = [ident, mode, fabr, anoFab, dataInc];

    return array;

}
function alterarItem(){
    const array = getData();
    let id = document.getElementById('identificadorInput');
    let mod = document.getElementById('modeloInput');
    let fab = document.getElementById('fabricanteInput');
    let anoDeFab = document.getElementById('anoDeFabricacaoInput');
    let dataInc = document.getElementById('dataDeInclusaoInput');

    id.value = array[0];
    mod.value = array[1];
    fab.value = array[2];
    anoDeFab.value = array[3];
    dataInc.value = array[4];

}
function criaLinha(item){

    botoes = document.createElement("div");
    botoes.classList.add("buttons");
    botao = document.createElement("button");
    botao.textContent = "Deletar";
    botao.classList.add("button");
    botao2 = document.createElement("button");
    botao2.textContent = "Alterar";
    botao2.classList.add("button");
    var ident = item.identificador + 1263292;
    var model = item.modelo;
    var fabricante = item.fabricante;
    var adfab = item.anoDeFabricacao;
    var ddInc = item.dataDeInclusao;


    tdFuncionario = document.createElement("td");

    if(item.funcionario != null){

        tdFuncionario.innerHTML = item.funcionario.getMatricula();
    }else{
        buttonFuncionario = document.createElement("button");
        buttonFuncionario.classList.add("button");
        buttonFuncionario.textContent = "Add Funcionário";
        tdFuncionario.onclick = function(){location.href="http://localhost:8080/funcionario_item.html?id=" + ident + "&mod=" + model + "&fab=" + fabricante + "&afab=" + adfab + "&di=" + ddInc};
        tdFuncionario.appendChild(buttonFuncionario);
    }
    botao.onclick = function(){location.href="http://localhost:8080/delete_item.html?id=" + ident + "&mod=" + model + "&fab=" + fabricante + "&afab=" + adfab + "&di=" + ddInc}
    botao2.onclick = function(){location.href="http://localhost:8080/alterar_item.html?id=" + ident + "&mod=" + model + "&fab=" + fabricante + "&afab=" + adfab + "&di=" + ddInc};
    botoes.appendChild(botao2);
    botoes.appendChild(botao);
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
    linha.appendChild(tdFuncionario);
    linha.appendChild(botoes);


    return linha;
}
function main(){

    let data = fazGet("/itens");
    let itens = JSON.parse(data);
    let tabela = document.getElementById('tbody');
    itens.forEach(element => {
        let linha = criaLinha(element);
        tabela.appendChild(linha);
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
    location.href = "http://localhost:8080";

}
function sendUpdate(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    var di = urlParams.get('di')
    var ident = urlParams.get('id');
    ident = ident - 1263292;
    var mode = document.querySelector('#modeloInput').value;
    var fabr = document.querySelector('#fabricanteInput').value;
    var anoFab = document.querySelector('#anoDeFabricacaoInput').value;



    let sendData = new XMLHttpRequest();

    sendData.open("PUT", "itens", true);
    sendData.setRequestHeader("Content-type", "application/json");

    var data = JSON.stringify({"identificador": ident, "modelo": mode, "fabricante": fabr, "anoDeFabricacao": anoFab,"dataDeInclusao": di});
    console.log(data);
    sendData.send(data);
    location.href= "http://localhost:8080";

}
function deleteItem(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    var di = urlParams.get('di')
    var ident = urlParams.get('id');
    ident = ident - 1263292;
    var mode = document.querySelector('#modeloInput').value;
    var fabr = document.querySelector('#fabricanteInput').value;
    var anoFab = document.querySelector('#anoDeFabricacaoInput').value;

    let sendData = new XMLHttpRequest();

    sendData.open("DELETE", "itens", true);
    sendData.setRequestHeader("Content-type", "application/json");

    var data = JSON.stringify({"identificador": ident, "modelo": mode, "fabricante": fabr, "anoDeFabricacao": anoFab,"dataDeInclusao": di});
    console.log(data);
    sendData.send(data);
    location.href= "http://localhost:8080";

}

function sendDataFunc(){

    var nome = document.querySelector('#nome').value;

    let sendData = new XMLHttpRequest();

    sendData.open("POST", "funcionarios", true);
    sendData.setRequestHeader("Content-type", "application/json");

    var data = JSON.stringify({"nome": nome});
    sendData.send(data);
    location.href = "http://localhost:8080";
}

function itemFunc(){

}