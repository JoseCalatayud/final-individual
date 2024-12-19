$(function () {



    function getProducts() {
        $('tbody').html("")
        $('.detalle').hide();
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
                            .off()
                            .on('click', function (event) {
                                console.log('Hola desde boton VerDetalle')
                                getProductsById($(event.target).attr('data-id'))
                                $('#botonCambios').text('Modificar').on('click', function () {
                                    $('#miModal').modal('show');
                                    $('#confirmarAccion').off().click(function() {
                                        guardarDatos()                                        ;
                                        $('#miModal').modal('hide');
                                    });

                                })
                            }))
                    );
                });
            },
            error: function (error) {
                console.log(error)
            },
            complete: function (data) {
                console.log(data)
            }

        })
    }
    function getProductsById(id) {
        $.ajax({
            url: `http://localhost:1234/api/productos/${id}`,
            success: function (data) {

                $('#id').val(data.id)
                $('#nombreProducto').val(data.nombre)
                $('#descripcionProducto').val(data.descripcion)
                $('#stockProducto').val(data.cantidad)
                $('#precioProducto').val(parseFloat(data.precio).toFixed(2) + ' €')
                console.log(data.id)
                if (data.id >= 0) {
                    $('.detalle').show()
                    $('#botonCambios')
                        .text('Modificar')


                }

            },
            error: function (error) {
                console.log(error)
            },
            complete: function (data) {

            }

        })
    }

    function comprarProducto(id) {

        $.ajax({
            url: `http://localhost:1234/api/productos/${id}/compra`,
            type: "POST",
            success: function (respuesta) {
                console.log("Producto comprado," + respuesta)
                refrescar()
            },
            error: function (error) {
                console.log('No tenemos stock suficiente ' + error)
            },
            complete: function () {

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
                refrescar()
            },
            error: (error) => {
                console.log(error)
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
                refrescar()
            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    function borrarProducto(id) {
        if (confirm("¿Está seguro?")) {
            $.ajax({
                url: `http://localhost:1234/api/productos/${id}`,
                type: 'DELETE',
                success: function (data) {
                    console.log('Borrado con exito' + data)
                    refrescar()

                },
                error: function (error) {
                    console.log(error)
                },
                complete: function () {
                }

            })
        }
    }
    function guardarDatos() {
        
            console.log('estoy en guardar')
            let id = $('#id').val();
            let nombre = $('#nombreProducto').val()
            let descripcion = $('#descripcionProducto').val();
            let cantidad = $('#stockProducto').val();
            let precio = ($('#precioProducto').val()).replace(/\s?€/g, '');
            let datos = {
                "id": id,
                "nombre": nombre,
                "descripcion": descripcion,
                "cantidad": cantidad,
                "precio": precio
            }
            if (datos.id != 0) {
                limpiarDetalle()
                modificarProducto(id, datos)

            } else {
                limpiarDetalle()
                insertarProducto(datos);
            }

        
    }

    function limpiarDetalle() {
        $('.detalle').hide();
        $('#id').val(0);
        $('#nombreProducto').val('')
        $('#descripcionProducto').val('');
        $('#stockProducto').val('');
        $('#precioProducto').val('');
    }

    $('.detalle').hide();
    getProducts()

    function refrescar() {
        $('.detalle').hide();
        $('#botonCambios').off()
        getProducts()
    }

    $('#refrescar').on('click', function () {
        refrescar()
    })

    $('#añadir').on('click', function () {
        limpiarDetalle();

        $('.detalle').show()
        $('#botonCambios').text('Crear Producto').off().on('click', function () {
            guardarDatos()
        })

    })

    $('#botonCancelar').on('click', function () {
        refrescar()
    })
    

})