$(function () {

    console.log("Hola desde javascript");

    function getProducts() {
        $.ajax({
            url: "http://localhost:1234/api/productos",
            success: function (data) {
                console.log(data)
            },
            error: function (error) {
                console.log(error)
            },
            complete: function () {
                console.log("Hemos acabado con exito")
            }

        })
    }
    function getProductsById(id) {
        $.ajax({
            url: `http://localhost:1234/api/productos/${id}`,
            success: function (data) {
                console.log(data)
            },
            error: function (error) {
                console.log(error)
            },
            complete: function () {
                console.log("Hemos acabado con exito")
            }

        })
    }
    function insertarProducto(datos){
        $.ajax({
            url:"http://localhost:1234/api/productos",
            type:"POST", 
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function(respuesta){
                console.log(respuesta)
            },
            error: (error)=>{
                console.log(error)
            }
        })
    }
    let datos = {
        "id": 1,
        "nombre": "Jose",
        "descripcion": "Hombre Guapo",
        "cantidad": 45,
        "precio": 19.99
    }
    $('#listar').on('click', ()=>{getProducts()})
    $('#listarID').on('click', ()=>{getProductsById(1)})
    $('#insertar').on('click', ()=>{insertarProducto(datos)})

})