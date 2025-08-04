package com.challengr.server.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "credentials")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // N   Метод авторизации  Пример метаданных
    // 0   Пароль SHA256      12345
    // 1   Телефон            +712345
    // 2   Двухфакторка       {"secret":12345}
    // 3   Гугл аккаунт       {"secret":12345}
    // 4   ВКонтакте          {"secret":12345}

    @Column(nullable = false)
    private Method method;

    @Column(nullable = false)
    private String metadata;

    public Credential(Method method, String metadata) {
        this.method = method;
        this.metadata = metadata;
    }

    public enum Method {
        PASSWORD(0),
        PHONE(1),
        TWOFA(2),
        GOOGLE(3),
        VK(4);

        public final int method;
        Method(int method) {
            this.method = method;
        }

        /**
         * Преобразование номера в метод
         *
         * @param number Численный объект
         * @return Метод авторизации
         */
        public static Method of(Object number) {
            return Method.values()[(int) number];
        }
    }
}
