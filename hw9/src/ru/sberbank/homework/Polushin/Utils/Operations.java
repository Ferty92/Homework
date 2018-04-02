package ru.sberbank.homework.Polushin.Utils;

/*
НЕ ИСПОЛЬЗУЕМЫЙ КЛАСС!!!
Возможно может пригодиться для улучшения.
 */
public enum Operations {
    GET(1) {
        @Override
        public String evoke(Money money) {

            return super.evoke(money);
        }
    },
    TAKE(2) {
        @Override
        public String evoke(Money money) {
            return super.evoke(money);
        }
    },
    CHECK(3) {
        @Override
        public String evoke(Money money) {
            return super.evoke(money);
        }
    },
    SET(4) {
        @Override
        public String evoke(Money money) {
            return super.evoke(money);
        }
    };

    private int code;

    Operations(int code) {
        this.code = code;
    }

    public String evoke(Money money) {
        return "Done";
    }
}
