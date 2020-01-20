var companieSelected;
var productoSelected;
var companiesList;
var variationsList = [];
var variationSelected;

// COMPANIE //

function showCompanies() {
    var button = '<div class="col-6"> <a href="#" onclick="openModalCompanie(0)" class="btn btn-info" >Nueva Empresa</a> </div><hr>';
    $.get('/companie', function (companies) {
        var title = 'Empresas';
        console.log(companies);
        companiesList = companies;
        var list = '';
        companiesList.forEach(function (companie) { 
            list = list
                + '<tr>' 
                    + '<td colspan="4">' + companie.name + '</td>'  
                    + '<td colspan="4">'   
                        +'<a href="#" onclick="openModalCompanie(' + companie.id + ')" id="button-edit" class="btn btn-info btn-sm btn-block">   Editar</a>' 
                        +'<a href="#" onclick="openDeleteModal(' + companie.id + ')"  class="btn btn-danger   btn-sm btn-block">   Eliminar</a>'
                    +'</td>'
                + '</tr>'
        });
        if(list.length > 0){
            list = ''
                + '<table class="table ">'
                    +'<thead>' 
                        +'<th colspan="4">Nombre</th>' 
                        +'<th colspan="4">Acciones</th>'
                    +'</thead>'
                    +'<tbody>'
                        + list
                    + '</tbody>'
                +'</table>';
        }else {
            list = "No existen Empresas registradas";
            document.getElementById('buttonProducts').disabled = true;
        }
        $('#list-companies').html(list);
        $('#title').html(title);
        
        $('#button').html(button);
    });
}

function addCompanie() { 
    if($('#nameCompanie').val().trim()){
        $.post({
            url: '/companie/addCompanie',
            contentType: 'application/json',
            data: JSON.stringify({  
                name: $('#nameCompanie').val(), 
            })
        }).then(showCompanies);      
        document.getElementById("nameCompanie").value = "";
        $("#addModalCompanie").modal('hide');
        document.getElementById('buttonProducts').disabled = false;
        showCompanies();
        messageSucces();
    }else{
        messageErrorForm();
    }
}

function editCompanie() {
    if($('#nameEditCompanie').val().trim()){ 
        $.ajax({
            url: '/companie/editCompanie',
            type: 'PUT', 
            data: JSON.stringify({
                id: companieSelected,
                name: $('#nameEditCompanie').val(), 
            }),
            contentType: 'application/json'
        }).then(showCompanies);
        $("#editModal").modal("hide"); 
        showCompanies();
        messageSucces();
    }else{
        messageErrorForm();
    }
}

function deleteCompanie() {
    var idCompanie = companieSelected; 
    $.ajax({
        url: '/companie/deleteCompanie/' + idCompanie, 
        type: 'DELETE',
        contentType: 'application/json'
    }).then(showCompanies);
    $("#deleteModal").modal("hide"); 
    messageDelete();
}

function openModalCompanie(idCompanie){
    if(idCompanie!=0){
        $("#editModal").modal("show");
        companieSelected = idCompanie;
        $.get('/companie/'+idCompanie, function (companie) { 
            document.getElementById('nameEditCompanie').value = companie.name;
        });
    }else{
        $("#addModalCompanie").modal("show");
    }
}

function openDeleteModal(idCompanie){
    $("#deleteModal").modal("show");
    companieSelected = idCompanie;
}
 
// PRODUCTS //

function showProducts() { 
    var title = 'Productos'; 
    var button = '<div class="col-6"> <a href="#" onclick="openModalProduct(0)" class="btn btn-info" >Nuevo producto</a> </div><hr>';
    var hasIva = false;
    $.get('/products/getProducts', function (products) { 
        console.log(products);
        var list = '';
        products.forEach(function (product) { 
            console.log(product);
            if(product.hasIva){
                hasIva = 'Si';
            }else{
                hasIva = 'No';
            }
            list = list
                + '<tr>' 
                    + '<td>' + product.name + '</td>'  
                    + '<td>' + product.stock + '</td>'  
                    + '<td> $' + product.cost + '</td>'  
                    + '<td> $' + product.price + '</td>'
                    + '<td> ' + hasIva + '</td>'
                    + '<td> ' + product.listVariations.length + '</td>'
                    + '<td>'    
                        +'<a href="#" onclick="openModalProduct(' + product.id +')" class="btn btn-info btn-sm btn-block">   Editar</a>' 
                        +'<a href="#" onclick="openDeleteModalProduct('  + product.id +')" class="btn btn-danger btn-sm btn-block">   Eliminar</a>'
                    +'</td>'
                + '</tr>'
        });
        if(list.length > 0){
            list = '' 
                + '<div ><table class="table ">'
                    +'<thead>' 
                        +'<th scope="col-4">Nombre</th>' 
                        +'<th scope="col-4">Stock</th>' 
                        +'<th scope="col-4">Costo</th>' 
                        +'<th scope="col-4">Precio</th>'  
                        +'<th scope="col-4">IVA</th>'  
                        +'<th scope="col-4">N. Variaciones</th>'  
                        +'<th scope="col-4">Acciones</th>'
                    +'</thead>'
                    +'<tbody>'
                        + list
                    + '</tbody>'
                +'</table></div>';
        }else{
            list = "No existen Productos registrados";
        }
        $('#list-companies').html(list);
        $('#title').html(title);
        $('#button').html(button);
    });
}

function openModalProduct(idProduct){
    var companies = '';
    companiesList.forEach(function (companie) { 
        companies = companies
        + '<option>' + companie.name + '</option>'
    });
    companies = '<select class="form-control" id="empresaProduct" >' + companies + '</select>';
    if(idProduct!=0){
        $("#modalProduct").modal("show");
        $.get('/products/getProduct/'+idProduct, function (product) { 
            document.getElementById('nameEditProduct').value = product.name;
            document.getElementById('stockEditProduct').value = product.stock;
            document.getElementById('costEditProduct').value = product.cost;
            document.getElementById('priceEditProduct').value = product.price;
            if(product.listVariations.length == 0 ){
                variationsList = [];
                $('#listEditVariations').html("No existen Variaciones");
            }else{
                tableVariations(product.listVariations);
            }
        });
        productoSelected = idProduct;
    }else{
        variationsList = [];
        $("#modalAddProduct").modal("show");
        $('#list-variations').html("No existen Variaciones");
    }
    $('#listCompaniesProduct').html(companies);
}

function openDeleteModalProduct(idProduct){
    $("#deleteModalProduct").modal("show");
    productoSelected = idProduct;
}

function addProduct() {
    var iva = false; 
    var newsVariations = [];
    if($("#ivaProduct").val() == "Si"){
        iva = true;
    }
    var companieProduct = companiesList.find(function (companie){
        return companie.name = $("#empresaProduct").val();
    });
    if($('#nameProduct').val().trim() && $('#stockProduct').val().trim() && $('#costProduct').val().trim() && $('#priceProduct').val().trim()){
        if($('#stockProduct').val() > 0 ){
            if($('#costProduct').val() < $('#priceProduct').val()){
                if( variationsList != undefined){
                    variationsList.forEach(function (variation){
                        if(variation.id == undefined){ 
                            newsVariations.push({
                                name: variation.name,
                                brand: variation.brand,
                                sku: variation.sku,
                                stock: variation.stock
                            });
                        }
                    });
                }
                $.post({
                    url: '/products/addProduct',
                    contentType: 'application/json',
                    data: JSON.stringify({ 
                        name: $('#nameProduct').val(),
                        stock: $('#stockProduct').val(),
                        cost: $('#costProduct').val(),
                        price: $('#priceProduct').val(),
                        hasIva: iva,
                        companiesId: companieProduct,
                        listVariations : newsVariations
                    })
                }).then(showProducts);
                document.getElementById('nameProduct').value = "";
                document.getElementById('stockProduct').value = "";
                document.getElementById('costProduct').value = "";
                document.getElementById('priceProduct').value = "";
                showProducts();
                $("#modalAddProduct").modal("hide"); 
                messageSucces();
            }else{
                messageErrorPriceCost();
            }
        }else{
            messageErrorStock();
        }
    }else{
        messageErrorForm();
    }
}

function editProduct() {
    var iva = false;  
    if($("#ivaProduct").val() == "Si"){
        iva = true;
    }
    console.log($("#ivaEditProduct").val());
    var companieProduct = companiesList.find(function (companie){
        return companie.name = $("#empresaEditProduct").val();
    });
    if($('#nameEditProduct').val().trim() && $('#stockEditProduct').val().trim() && $('#costEditProduct').val().trim() && $('#priceEditProduct').val().trim()){
        if($('#stockEditProduct').val() > 0){
            if($('#costEditProduct').val() < $('#priceEditProduct').val()){  
                variationsList.forEach(function (variation){
                    if(!variation.id){ 
                        $.post({
                            url: '/variations/addVariation',
                            contentType: 'application/json',
                            data: JSON.stringify({ 
                                name: $('#nameVariation').val(),
                                brand: $('#brandVariation').val(),
                                sku: $('#skuVariation').val(),
                                stock: $('#stockVariation').val()
                            })
                        }).then(tableVariations);  
                    }
                });
                $.ajax({
                    url: '/products/editProduct',
                    type: 'PUT', 
                    data: JSON.stringify({
                        id: productoSelected,
                        name: $('#nameEditProduct').val(),
                        stock: $('#stockEditProduct').val(),
                        cost: $('#costEditProduct').val(),
                        price: $('#priceEditProduct').val(),
                        hasIva: iva,
                        listVariations: variationsList,
                        companiesId: companieProduct
                    }),
                    contentType: 'application/json'
                }).then(showProducts); 
                document.getElementById('nameEditProduct').value = "";
                document.getElementById('stockEditProduct').value = "";
                document.getElementById('costEditProduct').value = "";
                document.getElementById('priceEditProduct').value = "";
                showProducts();
                $("#modalProduct").modal("hide");  
                messageSucces();
            }else{
                messageErrorPriceCost();
            }
        }else{
            messageErrorStock();
        }
    }else{
        messageErrorForm();
    }
}

function deleteProduct() { 
    var idProduct = productoSelected; 
    $.ajax({
        url: '/products/deleteProduct/' + idProduct, 
        type: 'DELETE',
        contentType: 'application/json'
    }).then(showProducts,showCompanies);
    $("#deleteModalProduct").modal("hide");
    messageDelete();  
} 

// VARIATIONS //

function tableVariations(listVariations){ 
    variationsList = listVariations;
    var listVariation = '';
    variationsList.forEach(function (variation) { 
        listVariation = listVariation
                + '<tr>' 
                + '<td>' + variation.name + '</td>'  
                + '<td>' + variation.brand + '</td>'  
                + '<td>' + variation.sku + '</td>'  
                + '<td>' + variation.stock + '</td>' 
                + '<td>'                   
                    +'<a href="#" onclick="openDeleteModalVariation('  + variation.id +')" class="btn btn-danger btn-sm btn-block">   Eliminar</a>'
                +'</td>'
            + '</tr>'
    });
    if(variationsList.length > 0){ 
        listVariation = ''
        + '<table class="table table-dark">'
            +'<thead>' 
                +'<th scope="col-4">Nombre</th>' 
                +'<th scope="col-4">Brand</th>' 
                +'<th scope="col-4">SKU</th>' 
                +'<th scope="col-4">Stock</th>'  
                +'<th scope="col-4">Acciones</th>'
            +'</thead>'
            +'<tbody>'
                + listVariation
            + '</tbody>'
        +'</table>';
    }else{ 
        listVariation = "No existen variaciones registradas";
    }   
    $('#listEditVariations').html(listVariation);
    $('#list-variations').html(listVariation);
} 

function addVariationList(){
    if($('#nameVariation').val().trim() && $('#brandVariation').val().trim() && $('#skuVariation').val().trim() && $('#stockVariation')){
        var sku = document.getElementById('skuVariation').value ;
        $.get('/variations/validateSku/'+ sku , function (data) { 
            if(data){
                if(document.getElementById('stockVariation').value > 0){
                    var newVariation = {
                        "name": document.getElementById('nameVariation').value,
                        "brand": document.getElementById('brandVariation').value,
                        "sku": document.getElementById('skuVariation').value,
                        "stock": document.getElementById('stockVariation').value
                    };
                    document.getElementById('nameVariation').value = "";
                    document.getElementById('brandVariation').value = "";
                    document.getElementById('skuVariation').value = "";
                    document.getElementById('stockVariation').value = "";
                    variationsList.push(newVariation);
                    tableVariations(variationsList);
                }else{
                    messageErrorStock();
                }
            }else{
                messageErrorSku();
            }
        });   
    }else{
        messageErrorForm();
    }
}

function editVariationList(){
    if($('#nameEditVariation').val().trim() && $('#brandEditVariation').val().trim() && $('#skuEditVariation').val().trim() && $('#stockEditVariation')){
        var sku = document.getElementById('skuEditVariation').value ;
        $.get('/variations/validateSku/'+ sku , function (data) { 
            if(data){
                if(document.getElementById('stockEditVariation').value > 0){
                    var newVariation = {
                        "name": document.getElementById('nameEditVariation').value,
                        "brand": document.getElementById('brandEditVariation').value,
                        "sku": document.getElementById('skuEditVariation').value,
                        "stock": document.getElementById('stockEditVariation').value
                    };
                    document.getElementById('nameEditVariation').value = "";
                    document.getElementById('brandEditVariation').value = "";
                    document.getElementById('skuEditVariation').value = "";
                    document.getElementById('stockEditVariation').value = "";
                    variationsList.push(newVariation);
                    tableVariations(variationsList);
                }else{

                }
            }else{

            }
        });     
    }else{
        messageErrorForm();
    }
}
 
function openDeleteModalVariation(idVariation){ 
    $("#deleteModalVariation").modal("show");
    variationSelected = idVariation;
}  

function deleteVariation() {
    if(variationSelected != undefined){
        var idVariation = variationSelected; 
        $.ajax({
            url: '/variations/deleteVariation/' + idVariation, 
            type: 'DELETE',
            contentType: 'application/json'
        });  
    }
    variationsList.splice(variationsList.findIndex(element => element.id === idVariation),1);        
    tableVariations(variationsList);
    $("#deleteModalVariation").modal("hide"); 
}

function messageSucces(){
    Swal.fire({
        icon: 'success',
        title: 'Datos guardados',
        showConfirmButton: false,
        timer: 2000
    });
}

function messageDelete(){
    Swal.fire({
        icon: 'success',
        title: 'Datos eliminados',
        showConfirmButton: false,
        timer: 2000
    });
}

function messageErrorStock(){
    Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'El valor de stock debe ser igual o mayor a 1',
        showConfirmButton: false,
        timer: 2000
    });
}

function messageErrorPriceCost(){
    Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'El costo debe ser menor al precio',
        showConfirmButton: false,
        timer: 2000
    });
}

function messageErrorSku(){
    Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'SKU ya regitrado',
        showConfirmButton: false,
        timer: 2000
    });
}

function messageErrorForm(){
    Swal.fire({
        icon: 'error',
        title: 'ERROR',
        text: 'Debe llenar todos los datos',
        showConfirmButton: false,
        timer: 2000
    });
}

showCompanies();