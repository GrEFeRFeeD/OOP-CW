package oop.CourseWork.model.productLog;

public enum ProductLogType {
    RECEIVING {
        public String getNameType() { return "Отримання"; }
    },

    CHECKINGOUT {
        public String getNameType() { return "Продаж"; }
    },

    RETURNING {
        public String getNameType() { return "Повернення"; }
    }
}
