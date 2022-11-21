
//Pegando dados de url
function fazGet(url){
    let request = new XMLHttpRequest();
    request.open("GET", url, false);
    request.send();
    return request.responseText;
}


//Pegando parámetros da url em alterar_item.html
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
//Carregando dados do item nos campos do formulário
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

//Carregamento de dados e criação de elementos(Criando tabela com dados de itens)
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
        tdFuncionario.innerHTML = item.funcionario;
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

//Função principal do index
function main(){

    let data = fazGet("/itens");
    let itens = JSON.parse(data);
    let tabela = document.getElementById('tbody');
    itens.forEach(element => {
        let linha = criaLinha(element);
        tabela.appendChild(linha);
    })

}

//Enviar dados de Post de item
function sendData(){
    let model = document.querySelector('#modelo').value;
    let manufacturer = document.querySelector('#fabricante').value;
    let yearFabrication = document.querySelector('#anoDeFabricacao').value;

    modelo = model.trim()
    manufacturer = manufacturer.trim();
    yearFabrication = yearFabrication.trim();

    let sendData = new XMLHttpRequest();

    sendData.open("POST", "itens", true);
    sendData.setRequestHeader("Content-type", "application/json");
    var data = JSON.stringify({"modelo": model, "fabricante": manufacturer, "anoDeFabricacao": yearFabrication});
    console.log(data);
    sendData.send(data);
    location.href = "http://localhost:8080";

}

//Enviar dados de Put de item
function sendUpdate(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    var di = urlParams.get('di')
    var ident = urlParams.get('id');
    ident = ident - 1263292;
    var mode = document.querySelector('#modeloInput').value;
    var fabr = document.querySelector('#fabricanteInput').value;
    var anoFab = document.querySelector('#anoDeFabricacaoInput').value;

    mode = mode.trim();
    fabr = fabr.trim();
    anoFab = anoFab.trim();

    let sendData = new XMLHttpRequest();

    sendData.open("PUT", "itens", true);
    sendData.setRequestHeader("Content-type", "application/json");

    var data = JSON.stringify({"identificador": ident, "modelo": mode, "fabricante": fabr, "anoDeFabricacao": anoFab,"dataDeInclusao": di});
    console.log(data);
    sendData.send(data);
    location.href= "http://localhost:8080";

}
//Delete de item
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
//Criação de funcionário
function sendDataFunc(){

    var nome = document.querySelector('#nome').value;
    nome = nome.trim()
    let sendData = new XMLHttpRequest();

    sendData.open("POST", "funcionarios", true);
    sendData.setRequestHeader("Content-type", "application/json");

    var data = JSON.stringify({"nome": nome});
    sendData.send(data);
    location.href = "http://localhost:8080";
}







//Pegando parámetros da url funcionario_item.html e adicionando nos campos do formulário
function itemFunc(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    var idItem = urlParams.get('id');
    idItem = idItem - 1263292;
    var modeloItem = urlParams.get('mod');
    var fabricanteItem = urlParams.get('fab');
    var anoFabItem = urlParams.get('afab');
    var dataIncItem = urlParams.get('di');

    let inputId = document.getElementById('ident');
    let inputModelo = document.getElementById('model');

    inputId.value = idItem;
    inputModelo.value = modeloItem;


}
//Enviando dados de atribuição de funcionário ao item
function sendItemFunc(){
     const queryString = window.location.search;
     const urlParams = new URLSearchParams(queryString);

     var idItem = urlParams.get('id');
     idItem = idItem - 1263292;
     var modeloItem = urlParams.get('mod');
     var fabricanteItem = urlParams.get('fab');
     var anoFabItem = urlParams.get('afab');
     var dataIncItem = urlParams.get('di');

     let matricula = document.querySelector('#func').value;

     let sendData = new XMLHttpRequest();

     sendData.open("PUT", "updateItemFunc", true);
     sendData.setRequestHeader("Content-type", "application/json");

    var data = JSON.stringify({"identificador": idItem, "modelo": modeloItem, "fabricante": fabricanteItem, "anoDeFabricacao": anoFabItem,"dataDeInclusao": dataIncItem, "funcionario": matricula});
    sendData.send(data);
    location.href = "http://localhost:8080";
}

//Main gerenciamento de funcionários
function funcionariosMain(){
    let data = fazGet("/funcionarios");
    let funcionarios = JSON.parse(data);
    let tabela = document.getElementById('bodyFunc');
    funcionarios.forEach(element => {
        let linha = criaLinhaFuncionario(element);
        tabela.appendChild(linha);
    })
}

function criaLinhaFuncionario(funcionario){

    //Atributos de funcionários
    var matricula = funcionario.matricula;
    var nome = funcionario.nome;
    var itensList = funcionario.itens;


    let funcionarioArea = document.createElement("div");
    funcionarioArea.classList.add("func");

    let infoFunc = document.createElement("h2");
    infoFunc.textContent = funcionario.matricula + " - " + funcionario.nome;

    let partButton = document.createElement("div");
    partButton.classList.add("buttons");
    let itens = document.createElement("button");
    itens.classList.add("button");
    itens.textContent = "Itens";
    itens.onclick = function(){location.href="http://localhost:8080/item_funcionario.html?matricula=" + matricula}

    let editar = document.createElement("button");
    editar.classList.add("button");
    editar.textContent = "Editar";

    let apagar = document.createElement("button");
    apagar.classList.add("button");
    apagar.textContent = "Apagar";

    partButton.appendChild(itens);
    partButton.appendChild(editar);
    partButton.appendChild(apagar);


     funcionarioArea.appendChild(infoFunc);
     funcionarioArea.appendChild(partButton);

    return funcionarioArea;
}

function mainItensFuncionario(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    var matricula = urlParams.get('matricula');
    var data = fazGet("/funcionarios");
    var funcionarios = JSON.parse(data);
    var lista;
    let tabela = document.getElementById("tbody");
    funcionarios.forEach(element => {
        if(element.matricula == matricula){
            lista = element.itens;
        }
    })
    lista.forEach(element => {
        let linha = document.createElement("tr");
        let identificador = document.createElement("td");
        let modelo = document.createElement("td");
        let fabricante = document.createElement("td");
        let anoDeFabricacao = document.createElement("td");
        let dataDeInclusao = document.createElement("td");
        let ultimaAtualizacao = document.createElement("td");
        let button = document.createElement("button");

        button.classList.add("button");
        button.textContent = "Remover";
        button.onclick = function(){location.href="http://localhost:8080/remove_item_func.html?matricula=" + matricula + "&id=" + element.identificador + "&modelo=" + element.modelo + "&fabricante=" + element.fabricante + "&anoDeFabricacao=" + element.anoDeFabricacao + "&dataDeInclusao=" + element.dataDeInclusao + "&ultimaAtualizacao=" + element.ultimaAtualizacao};

        identificador.innerHTML = element.identificador;
        modelo.innerHTML = element.modelo;
        fabricante.innerHTML = element.fabricante;
        anoDeFabricacao.innerHTML = element.anoDeFabricacao;
        dataDeInclusao.innerHTML = element.dataDeInclusao;
        ultimaAtualizacao.innerHTML = element.ultimaAtualizacao;

        linha.appendChild(identificador);
        linha.appendChild(modelo);
        linha.appendChild(fabricante);
        linha.appendChild(anoDeFabricacao);
        linha.appendChild(dataDeInclusao);
        linha.appendChild(ultimaAtualizacao);

        linha.appendChild(button);

        tabela.appendChild(linha);

    })
}

//Main na página remove_item_func
function mainRemoveItemFunc(){
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    let matriculaParam = urlParams.get("matricula");
    var botaoVoltar = document.getElementById('botao');
    botaoVoltar.onclick = function(){location.href="http://localhost:8080/item_funcionario.html?matricula=" + matriculaParam};


    document.getElementById('ident').value = urlParams.get('id');
    document.getElementById('model').value = urlParams.get('modelo');
    document.getElementById('fabri').value = urlParams.get('fabricante');
    document.getElementById('anoDeFab').value = urlParams.get('anoDeFabricacao');
}

function removerItemFunc(){
     const queryString = window.location.search;
     const urlParams = new URLSearchParams(queryString);

     var identificador = urlParams.get("id");
     var modelo = urlParams.get("modelo");
     var fabricante = urlParams.get("fabricante");
     var anoDeFabricacao = urlParams.get("anoDeFabricacao");
     var dataDeInclusao = urlParams.get("dataDeInclusao");
     var matricula = urlParams.get("matricula");

     var sendData = new XMLHttpRequest();

     sendData.open("PUT", "funcionarios", true);
     sendData.setRequestHeader("Content-type", "application/json");

     var data = JSON.stringify({"identificador": identificador});
     sendData.send(data);
}