package oop.CourseWork.model.product;

public enum ProductGroup {
    MILK_PRODUCTS { public String getName() {return "Молочна продукція"; } },
    MEAT { public String getName() {return "М'ясні вироби"; } },
    BAKERY_PRODUCTS { public String getName() {return "Хлібобулочні вироби"; } },
    CEREAL { public String getName() {return "Крупи"; } },
    PASTA { public String getName() {return "Макаронні вироби"; } },
    VEGETABLES { public String getName() {return "Овочі"; } },
    FRUITS { public String getName() {return "Фрукти"; } },
    CONFECTIONERY { public String getName() {return "Кондитерські вироби"; } },
    DIETARY_FATS { public String getName() {return "Харчові жири"; } },
    WATER { public String getName() {return "Вода"; } },
    ALCOHOL { public String getName() {return "Алкоголь"; } },
    SNACKS { public String getName() {return "Снеки"; } },
    LACTOSE_FREE { public String getName() {return "Безлактозна продукція"; } },
    GLUTEN_FREE { public String getName() {return "Безглютенна продукція"; } },
    GMO_FREE { public String getName() {return "Продукція без ГМО"; } },
    BABY_PRODUCTS { public String getName() {return "Дитяча продукція"; } }
}
