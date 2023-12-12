package main.Enums;

public enum RequestType {
        LOGINADMIN,
        ADMINCREATEADMIN,


        //TODO Часть Админа
        //Работа с аккаунтами
        ADMINMENUACCOUNTSADMINS,
        ADMINMENUACCOUNTSACCOUNTANTS,
        ADMINMENUACCOUNTSCLIENTS,

        //Работа с поставщиками
        ADMINMENUSUPPLIERS,

        //Работа с товарами
        ADMINMENUPRODUCTS,

        //Работа с Отчетами
        ADMINMENUREPORTS,

        //Работа с отзывами
        ADMINMENUREVIEWS,

        //Работа с заказами
        ADMINMENUORDERS,


        //TODO Часть бухгалтера
        ACCOUNTANTLOGIN,
        ACCOUNTANTREPORT,
        ACCOUNTANTVIEWORDERS,
        ACCOUNTANTVIEWFINALORDERS,
        DELIVERYPRICE,



        //TODO Часть клиента
        CLIENTLOGIN,
        CLIENTORDER,
        CLIENTREVIEW,



        //TODO удалить эти части
        REGISTER,
        LOGIN
}

