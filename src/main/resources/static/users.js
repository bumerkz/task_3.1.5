

const tbody = document.querySelector("#tablebody");
const tableOfUsers = document.querySelector("#tableAllUsers");
let str = "";
$(documet).ready(async function () {
    let users = await fetch("http://localhost:8080/allUsers").then(r => r.json());
    const template = document.querySelector('#product');
    users.forEach((u) => {
        let rol = "";
        u.roles.forEach((u) => {
            rol += u.withoutPrefix + "  ";
        });
        str += `<tr id="dele${u.id}"><td>${u.id}</td> 
<td>${u.firstname}</td>
<td>${u.fullname}</td>
<td>${u.email}</td>
<td>${u.password}</td>
 <td>${rol}</td>
<td><button class="btn btn-info editbtn" >Edit</button></td>
<td><button class="btn btn-danger delbtn">Delete</button></td></tr>`;
    })
    tbody.innerHTML = str;                                             // содержимое html в виде строки
})


//Загрузка списка всех ролей
$(document).ready(async function (){
    let rolles  = await  fetch("http://localhost:8080/allRoles").then(r => r.json());
    let rol = "";
    rolles.forEach((r) => rol += `<option value="${r.role}">${r.withoutPrefix}</option>`);
    let sel = `<select name="sele" onchange="console.log($('select').val())" id="select"
multiple  class="form-control sel" size="2">
${rol}
</select>`;
    document.getElementById('selectorEdit').innerHTML = sel;
})

//для изменения ролей

async function rol(userRol) {
    console.log(userRol);
    let rolles = await fetch("http://localhost:8080/allRoles").then(r => r.json());
    let rol = "";
    for (let n=0; n<rolles.length; n++) {
        rol +=`<option value="${rolles[n].role}">${rolles[n].withoutPrefix}</option>`;
    }
    let sel = `<select name="sele" onchange ="console.log($('#selectEdit').val())" id= "selectEdit"
     multiple class="form -control sel" size="2">
${rol}
</select>`;
    document.getElementById('selectorEdit').innerHTML = sel;
}


//доступ на нужную страницу по роли принципиала

$(document).ready(async function (){
    let principal = await fetch ("http://localhost:8080/myPrincipal").then(r => r.json());
    for (let i=0; i<principal.roles.length; i++) {
        if (principal.roles[i].role === 'ROLE_ADMIN') {
            return;
        }
    }
    $('#userpanel').hide();
    lookTablePrincipal();
})


//Страница юзера по боковой ссылке, вставится в admin.html по ссылкке
async function lookTablePrincipal() {
    let princ = await fetch("http://localhost:8080/myPrincipal").then(r => r.json());
    $('#centralTable').hide();
    let rol = "";
    princ.roles.forEach((u) => {
        rol += u.withoutPrefix + "  \n";
    });
    let strPr = `<tr><td>${princ.id}</td>
<td>${princ.firstname}</td>
<td>${princ.fullname}</td>
<td>${princ.email}</td>
<td>${princ.password}</td>
<td>${rol}</td></tr>`;
    let tab = `
<div class="col-10 bg-light vh-150 p-4">
<h1>User information-page</h1> 
<div class=" show active border" id="user_panel" role="tabpanel" aria-labelledby="home-tab">
<td class="bg-white p-0"></td>
<h4 class=" px-5">About User</h4>
<table class="table table-striped ">
<thead>
<th>ID</th>
<th>Firstname</th>
<th>Fullname</th>
<th>Email</th>
<th>Password</th>
<th>Roles</th>
</thead>         
<tbody id="tbodyPrincip">${strPr}</tbody>
</table>
</div>
</div>`
    document.getElementById('us_tab').innerHTML = tab;
}


//показать моего Principal
$('.action').on('click', async function () {
    lookTablePrincipal();
});
$(document).ready(async function(){
    let principal = await fetch("http://localhost:8080/myPrincipal").then( r => r.json());
    let rol = "";
    principal.roles.forEach((r) => {
        rol +=r.withoutPrefix + " "
    });
    document.getElementById('black').innerHTML = `<b>${principal.email}</b>` + ` with roles: ` +
        `<b>${rol}</b>`;
})

//создание нового юзера
const firstnameCreate = document.getElementById('firstnameCreate');
const fullnameCreate = document.getElementById('fullnameCreate');
const emailCreate = document.getElementById('emailCreate');
const passwordCreate = document.getElementById('passwordCreate');

$(document).ready(async function () {
    $('.btnCreate').on('click', async function (e) {
        e.preventDefault();                                                       // отмена действия браузера для своего события
        let u = await fetch('http://localhost:8080/makeUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({                       // для преобразования обьектов в json
                firstname: firstnameCreate.value,
                fullname: fullnameCreate.value,
                email: emailCreate.value,
                password: passwordCreate.value,
                roles: $('#select').val()
            })
        }).then(f => f.json());
        firstnameCreate.value = "";
        fullnameCreate.value = "";
        emailCreate.value = "";
        passwordCreate.value = "";
        document.getElementById('select').value = "";
        document.getElementById('ad_pan').click();
        let rol = "";
        u.roles.forEach((u) => {
            rol += u.withoutPrefix + " \n";
        });
        row = ` <tr id="dele${u.id}"><td>${u.id}</td>
<td>${u.firstname}</td>
<td>${u.fullname}</td>
<td>${u.email}</td>
<td>${u.password}</td>
<td>${rol}</td>
<td><button class="btn btn-info editbtn">Edit</button></td>
<td><button class="btn btn-danger delbtn">Delete</button></td></tr>`;
        tbody.insertAdjacentHTML('beforeend', row);
    })
})

const idEdit = document.getElementById('idFormEdit');
const firstnameEdit = document.getElementById('firstnameFormEdit');
const fullnameEdit = document.getElementById('fullnameFormEdit');
const emailEdit = document.getElementById('emailFormEdit');
const passwordEdit = document.getElementById('passwordFormEdit');
let row = "";


//кнопка Edit в модальном окне
$(document).ready(function () {
    $('#submitEdit').on('click', async function (e) {
        e.preventDefault();
        let id = idEdit.value;
        let one = document.getElementById(`dele${id}`);
        let u = await fetch('http://localhost:8080/changeUser', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: idEdit.value,
                firstname: firstnameEdit.value,
                fullname: fullnameEdit.value,
                email: emailEdit.value,
                password: passwordEdit.value,
                roles: $('#selectEdit').val()
            })
        }).then(u => u.json());
        let rol = "";
        u.roles.forEach((u) => {
            rol += u.withoutPrefix + " \n";
        });
        row = ` <tr id="dele${u.id}"><td>${u.id}</td>
<td>${u.firstname}</td>
<td>${u.fullname}</td>
<td>${u.email}</td>
<td>${u.password}</td>
<td>${rol}</td>
<td><button class="btn btn-info editbtn">Edit</button></td>
<td><button class="btn btn-danger delbtn">Delete</button></td></tr>`;
        one.innerHTML = row;
        $('#editModal').modal('hide');
    })
});





//функция для поиска нужного юзера в таблице, соответствующего кнопке selector и выполняющего с ним функцию handle (удаление, редактирование, взяла готовый)
const on = (element, even, selector, handle) => {
    element.addEventListener(even, e => {                              //назначение более одного обработчика события
        if (e.target.closest(selector)) {                          // метод поднимается вверх от элемента и проверяет каждого родителя, прекращается поиск при соотвествии селектору
            handle(e)
        }
    })
}

//для модального окно при изменении
on(document, 'click', '.editbtn', (e) => {
    e.preventDefault();
    const father = e.target.parentNode.parentNode;        // возвращает родителя
    const id = father.firstElementChild.innerHTML;
    const firstname = father.children[1].innerHTML;
    const fullname = father.children[2].innerHTML;
    const email = father.children[3].innerHTML;
    const password = father.children[4].innerHTML;
    const role = father.children[5].innerHTML;
    let rolAr = role.replace(/[^A-Za-z]+/, " ").trim().split(/\s+/);
    rol(rolAr);
    $('#idFormEdit').val(id);
    $('#firstnameFormEdit').val(firstname);
    $('#fullnameFormEdit').val(fullname);
    $('#emailFormEdit').val(email);
    $('#passwordFormEdit').val(password);
    $('#editModal').modal();
})


const idValue = document.querySelector('#idDelete');
//для модального окна при удалении
on(document, 'click', '.delbtn', (e) => {
    e.preventDefault();
    const father = e.target.parentNode.parentNode;
    const id = father.firstElementChild.innerHTML;
    const firstname = father.children[1].innerHTML;
    const fullname = father.children[2].innerHTML;
    const email = father.children[3].innerHTML;
    const password = father.children[4].innerHTML;
    const role = father.children[5].innerHTML;
    $('#idDelete').val(id);
    $('#firstnameDelete').val(firstname);
    $('#fullnameDelete').val(fullname);
    $('#emailDelete').val(email);
    $('#passwordDelete').val(password);
    $('#roleDelete').val(role);
    $('#deleteModal').modal();
})

//кнопка Delete в модальном окне
$(document).ready(function () {
    $('#submitDelete').on('click', async function (e) {
        e.preventDefault();
        let id = idValue.value;
        let deleteUser = document.getElementById(`dele${id}`);
        await fetch(`http://localhost:8080/delete/${id}`, {
            method: 'DELETE'
        });
        deleteUser.remove();
        $('#deleteModal').modal('hide');
    });
});