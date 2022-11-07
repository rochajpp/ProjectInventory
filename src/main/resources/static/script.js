const url = 'http://localhost:8080';
const itemEndpoint = url + '/itens';

function loadItems(){
    fetch(itemEndpoint)
        .then((response) => response.json())
        .then((data) => {
            var i = 0;
            for(const [key, value] of Object.entries(data)){
               console.log(data)
                document.getElementById('identificationNumber').innerHTML = data.get(i).identificador;

            }

        });
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