$(function () {



    function getProducts() {
        $('tbody').empty();
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
                                mostrarModalConfirmacion('Borrar producto', 'Confirmar borrado', () => {
                                    borrarProducto($(event.target).attr('data-id'));
                                })
                                console.log('Hola desde botonBorrar')
                            }))
                        .append($('<td>').attr('class', 'tdBotones')
                            .append($('<button>')
                                .attr('class', 'botonMaestro')
                                .attr('data-id', element.id)
                                .text('Comprar'))
                            .on('click', function (event) {
                                mostrarModalConfirmacion('Comprar producto', 'Confirmar compra', () => {
                                    comprarProducto($(event.target).attr('data-id'));
                                })

                            }))
                        .append($('<td>').attr('class', 'tdBotones')
                            .append($('<button>')
                                .attr('class', 'botonMaestro')
                                .attr('data-id', element.id)
                                .text('Detalle'))
                            .off()
                            .on('click', function (event) {
                                console.log('Hola desde boton VerDetalle')
                                verDetalleProducto($(event.target).attr('data-id'))
                                $('#tituloDetalle').text('Detalles del producto')
                                $('#botonCambios').text('Modificar').off().on('click', function () {
                                    if (verificarCampos()) {
                                        mostrarModalConfirmacion('Modificar producto', '¿Modificar producto?', () => {
                                            guardarDatos();
                                        })


                                    }

                                })
                            }))
                    );
                });
            },
            error: function (error) {
                mostrarError("Error al listar los productos")
                console.log(error)
            },


        })
    }
    function verDetalleProducto(id) {
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
                mostrarError("Error al recuperar los datos");
                console.log(error)
            },
        })
    }

    function comprarProducto(id) {

        $.ajax({
            url: `http://localhost:1234/api/productos/${id}/compra`,
            type: "POST",
            success: function (respuesta) {
                refrescar()
            },
            error: function (error) {
                mostrarError("Stock insuficiente")
            },
            
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
                mostrarError('Fallo al guardar el producto')
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
                refrescar()
            },
            error: function (error) {
                mostrarError('Fallo al modificar el producto')
                console.log(error)
            }
        })
    }

    function borrarProducto(id) {
        $.ajax({
            url: `http://localhost:1234/api/productos/${id}`,
            type: 'DELETE',
            success: function (data) {                
                refrescar()

            },
            error: function (error) {
                mostrarError("Fallo al eliminar el producto")
                console.log(error)
            },
           
        })

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



    function refrescar() {
        $('.detalle').hide();
        $('#botonCambios').off()
        getProducts()
    }

    function mostrarModalConfirmacion(titulo, mensaje, callback) {
        $('#miModalLabel').text(titulo);
        $('#preguntaModal').text(mensaje);
        $('#miModal').modal('show');
        $('#confirmarAccion').off().click(function () {
            callback();
            $('#miModal').modal('hide');
        });
    }




    function verificarCampos() {
        let id = $('#id').val();
        let nombre = $('#nombreProducto').val().trim();
        let descripcion = $('#descripcionProducto').val().trim();
        let cantidad = $('#stockProducto').val().trim();
        let precio = $('#precioProducto').val().trim().replace(/\s?€/g, '');

        if (!nombre) {
            mostrarError("El nombre del producto es obligatorio.");
            return false;
        }

        if (!descripcion) {
            mostrarError("La descripción del producto es obligatoria.");
            return false;
        }

        if (!cantidad || isNaN(cantidad) || parseInt(cantidad) < 0) {
            mostrarError("La cantidad debe ser un número válido y no negativo.");
            return false;
        }

        if (!precio || isNaN(precio) || parseFloat(precio) < 0) {
            mostrarError("El precio debe ser un número válido y no negativo.");
            return false;
        }

        return true;
    }
    function mostrarError(mensaje) {
        $('#preguntaModalValidar').text(mensaje);
        $('#miModalValidar').modal('show');
    }

    $('#botonCancelar').on('click', function () {
        refrescar()
    })
    $('#añadir').on('click', function () {
        limpiarDetalle();
        $('#tituloDetalle').text('Datos del nuevo producto')
        $('.detalle').show()
        $('#botonCambios').text('Crear Producto').off().on('click', function () {
            if (verificarCampos()) {
                $('#miModalLabel').text('Añadir producto')
                $('#preguntaModal').text('¿Seguro que quieres añadir?')
                $('#miModal').modal('show');
                $('#confirmarAccion').off().click(function () {
                    guardarDatos();
                    $('#miModal').modal('hide');
                });

            }

        })


    })
    $('#refrescar').on('click', function () {
        refrescar()
    })
    $('.detalle').hide();
    getProducts()

})