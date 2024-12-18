$(function () {



    function getProducts() {
        $('tbody').html("")
        $.ajax({
            url: "http://localhost:1234/api/productos",
            success: function (data) {
                data.forEach(element => {
                    $('tbody').append($('<tr>')
                        .append($('<td>').text(element.nombre))
                        .append($('<td>').text(element.cantidad))
                        .append($('<td>').attr('class', 'precio').text(parseFloat(element.precio).toFixed(2) + " €"))
                        .append($('<td>').attr('class', 'tdBotones')
                            .append($('<button>')
                                .attr('class', 'botonMaestro')
                                .attr('data-id', element.id)
                                .text('Borrar'))
                            .on('click', function (event) {
                                borrarProducto($(event.target).attr('data-id'))
                                console.log('Hola desde botonBorrar')
                            }))
                        .append($('<td>').attr('class', 'tdBotones')
                            .append($('<button>')
                                .attr('class', 'botonMaestro')
                                .attr('data-id', element.id)
                                .text('Comprar'))
                            .on('click', function (event) {
                                comprarProducto($(event.target).attr('data-id'))
                                console.log('Hola desde boton comprar')
                            }))
                        .append($('<td>').attr('class', 'tdBotones')
                            .append($('<button>')
                                .attr('class', 'botonMaestro')
                                .attr('data-id', element.id)
                                .text('Detalle'))
                            .on('click', function (event) {
                                console.log('Hola desde boton VerDetalle')
                                getProductsById($(event.target).attr('data-id'))
                            }))
                    );
                });
            },
            error: function (error) {
                console.log(error)
            },
            complete: function () {

            }

        })
    }
    function getProductsById(id) {
        $.ajax({
            url: `http://localhost:1234/api/productos/${id}`,
            success: function (data) {
                $('.detalle').show()
                $('#id').val("hola")
                $('#nombreProducto').val(data.nombre)
                $('#descripcionProducto').val(data.descripcion)
                $('#stockProducto').val(data.cantidad)
                $('#precioProducto').val(parseFloat(data.precio).toFixed(2)+' €')
                console.log(data.nombre)
                
            },
            error: function (error) {
                console.log(error)
            },
            complete: function (data) {
                
            }

        })
    }
    function insertarProducto(datos) {
        $.ajax({
            url: "http://localhost:1234/api/productos",
            type: "POST",
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (respuesta) {
                console.log(respuesta)
            },
            error: (error) => {
                console.log(error)
            }
        })
    }

    function comprarProducto(id) {

        $.ajax({
            url: `http://localhost:1234/api/productos/${id}/compra`,
            type: "POST",
            success: function (respuesta) {
                console.log("Producto comprado," + respuesta)
            },
            error: function (error) {
                console.log('No tenemos stock suficiente ' + error)
            },
            complete: function () {
                getProducts()
            }
        })

    }

    function modificarProducto(id, datos) {
        $.ajax({
            url: `http://localhost:1234/api/productos/${id}`,
            type: "PUT",
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                console.log(data)
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    function borrarProducto(id) {
        if(confirm("¿Está seguro?")){

            $.ajax({
                url: `http://localhost:1234/api/productos/${id}`,
                type: 'DELETE',
                success: function (data) {
                    console.log('Borrado con exito' + data)
                },
                error: function (error) {
                    console.log(error)
                },
                complete: function () {
    
                    getProducts()
                }
    
            })
        }
    }

    let datos = {
        "id": 0,
        "nombre": "Jose",
        "descripcion": "Hombre",
        "cantidad": 45,
        "precio": 19.99
    }
    let datos1 = {
        "id": 8,
        "nombre": "pablo",
        "descripcion": "familia perez",
        "cantidad": 45,
        "precio": 19.99
    }
    $('#listar').on('click', () => { getProducts() })
    $('#listarID').on('click', () => { getProductsById(1) })
    $('#insertar').on('click', () => { insertarProducto(datos) })
    $('#borrar').on('click', () => { borrarProducto(11) })
    $('#modificar').on('click', () => modificarProducto(8, datos1))
    $('#comprar').on('click', () => comprarProducto(2))
    $('.detalle').hide();
    getProducts()

})