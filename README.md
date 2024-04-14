Api key вставлять в build.gradle :app в параметр buildConfigField "API_KEY"


UPD ссылка на видео работы приложения https://disk.yandex.ru/i/LKG0KzLuj-f8UA
UPD Согласно требованиям из письма прилагаю примеры запросов и ответов

1) стартовый запрос
GET https://api.kinopoisk.dev/v1.4/movie?page=1&limit=10&selectFields=id&selectFields=name&selectFields=year&selectFields=shortDescription&selectFields=poster&selectFields=isSeries&selectFields=rating&selectFields=ageRating&selectFields=genres&selectFields=networks&selectFields=countries&notNullFields=id&notNullFields=name&notNullFields=year&year=1874-2024
пример ответа
{
    "docs": [
        {
            "rating": {
                "kp": 8.823,
                "imdb": 8.5,
                "filmCritics": 6.8,
                "russianFilmCritics": 100,
                "await": null
            },
            "id": 535341,
            "name": "1+1",
            "year": 2011,
            "poster": {
                "url": "https://image.openmoviedb.com/kinopoisk-images/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/orig",
                "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/1946459/bf93b465-1189-4155-9dd1-cb9fb5cb1bb5/x1000"
            },
            "genres": [
                {
                    "name": "драма"
                },
                {
                    "name": "комедия"
                },
                {
                    "name": "биография"
                }
            ],
            "countries": [
                {
                    "name": "Франция"
                }
            ],
            "shortDescription": "Аристократ на коляске нанимает в сиделки бывшего заключенного. Искрометная французская комедия с Омаром Си",
            "ageRating": 18,
            "isSeries": false,
            "networks": null
        }
    ],
    "total": 293385,
    "limit": 1,
    "page": 1,
    "pages": 293385
}

2)запрос с фильтрами

GET https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&selectFields=id&selectFields=name&selectFields=year&selectFields=shortDescription&selectFields=poster&selectFields=isSeries&selectFields=rating&selectFields=ageRating&selectFields=genres&selectFields=networks&selectFields=countries&notNullFields=id&notNullFields=name&notNullFields=year&isSeries=true&year=1874-2024&genres.name=%D0%BA%D0%BE%D0%BC%D0%B5%D0%B4%D0%B8%D1%8F&countries.name=%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D1%8F

{
    "docs": [
        {
            "id": 1355059,
            "name": "Беспринципные",
            "rating": {
                "kp": 7.659,
                "imdb": 6.3,
                "filmCritics": 0,
                "russianFilmCritics": 50,
                "await": null
            },
            "year": 2020,
            "poster": {
                "url": "https://image.openmoviedb.com/kinopoisk-images/10592371/e2db42c4-4176-4a0f-b933-488412cd06a5/orig",
                "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/10592371/e2db42c4-4176-4a0f-b933-488412cd06a5/x1000"
            },
            "genres": [
                {
                    "name": "комедия"
                }
            ],
            "countries": [
                {
                    "name": "Россия"
                }
            ],
            "ageRating": 18,
            "shortDescription": "На Патриках — новые плуты и новые романы. Продолжение хитовой комедии, теперь — с Милошем Биковичем",
            "isSeries": true,
            "networks": {
                "items": [
                    {
                        "name": "Кинопоиск"
                    }
                ]
            }
        }
    ],
    "total": 1199,
    "limit": 1,
    "page": 1,
    "pages": 1199
}

3) поисковой запрос
GET https://api.kinopoisk.dev/v1.4/movie/search?page=3&limit=10&query=kingsman
{
    "docs": [
        {
            "id": 1013576,
            "name": "",
            "alternativeName": "Kingsman",
            "enName": "",
            "type": "movie",
            "year": 2015,
            "description": "",
            "shortDescription": "",
            "movieLength": 0,
            "isSeries": false,
            "ticketsOnSale": false,
            "totalSeriesLength": null,
            "seriesLength": null,
            "ratingMpaa": null,
            "ageRating": null,
            "top10": null,
            "top250": null,
            "typeNumber": 1,
            "status": null,
            "names": [
                {
                    "name": "Kingsman",
                    "language": null,
                    "type": "Original title on kinopoisk"
                }
            ],
            "externalId": {
                "kpHD": null
            },
            "logo": null,
            "poster": {
                "url": null,
                "previewUrl": null
            },
            "backdrop": {
                "url": null,
                "previewUrl": null
            },
            "rating": {
                "kp": 0,
                "imdb": 0,
                "filmCritics": 0,
                "russianFilmCritics": 0,
                "await": 0
            },
            "votes": {
                "kp": 111,
                "imdb": 0,
                "filmCritics": 0,
                "russianFilmCritics": 0,
                "await": 0
            },
            "genres": [
                {
                    "name": "короткометражка"
                },
                {
                    "name": "комедия"
                }
            ],
            "countries": [
                {
                    "name": "Франция"
                }
            ],
            "releaseYears": []
        }
    ],
    "total": 16,
    "limit": 1,
    "page": 1,
    "pages": 16
}

4) запрос постеров
GET https://api.kinopoisk.dev/v1.4/image?page=1&limit=10&selectFields=movieId&selectFields=previewUrl&notNullFields=movieId&notNullFields=previewUrl&movieId=749540





{
    "docs": [
        {
            "movieId": 749540,
            "previewUrl": "https://image.openmoviedb.com/kinopoisk-images/1777765/bc5d3f20-d1bd-4791-b2f7-ca2e4ac7205b/360",
            "id": "632e5f46aff710671ba9dd8a"
        }
    ],
    "total": 197,
    "limit": 1,
    "page": 1,
    "pages": 197
}

5) запрос рецензий
GET https://api.kinopoisk.dev/v1.4/review?page=1&limit=10&selectFields=movieId&selectFields=review&selectFields=author&selectFields=type&notNullFields=movieId&notNullFields=review&notNullFields=author&notNullFields=type&movieId=749540
{
    "docs": [
        {
            "movieId": 749540,
            "type": "Позитивный",
            "review": "У режиссера Мэттью Вона были большие планы на оригинальный комикс под названием «Секретная служба». Он видел в нем потенциал и очень хотел экранизировать его, как полнометражный фильм. И вот его планы претворились в жизнь с подачи студии 20 Век Фокс, которая помогла с правами и реализацией многообещающего проекта.\r\n\r\nГлавным героем фильма становится молодой парень Эггси, житель не самого благополучного района Лондона. Он когда-то прошел службу в армии, но это его особо не вдохновило. Парень в одной квартирке уживается с мамой, младшей сестрой и отчимом. Но все изменилось тогда, когда в двери Эггси постучал Гарри Харт, который совсем не просто так появился в жизни бунтующего парня из трущоб.\r\n\r\nГарри – секретный агент таинственной организации, которая никому не подчиняется. Все дело в том, что «Kingsman» является полностью автономной организацией, которая вычисляет международную угрозу и действует по собственным соображениям. И вот Гарри очень хочет, чтобы Эггси прошел все испытания и занял свое законное место рядом с ним. Но впереди много заданий и не все из них легко решить.\r\n\r\nКак говорил режиссер Мэттью Вон, ему было важно, чтобы этот фильм воспринимали совсем не так, как еще одну историю в стиле Джеймса Бонда. Это скорее именно пародия на всю «бондиану». И фильм на самом деле вышел более смелым и вызывающим, режиссер не боится смеяться над жанровыми клише и в целом делает все, чтобы зритель во время просмотра навсегда забыл о Бонде.\r\n\r\nГлавная роль в фильме досталась молодому британцу Тэрону Эджертону, и он чудо как хорош в образе Эггси. Эджертон играет немного нагловатого, но очень покладистого парня, с которым спокойно можно идти в разведку. А когда он надевает фирменный костюм Kingsman, то в момент превращается из уличного хулигана в элегантного джентльмена.\r\n\r\nОчень хорош в фильме Валентайн, главный злодей. Его уже сыграл тут матерый Сэмюэл Л. Джексон. И вот ему тоже дали развернуться со всей творческой силой. Это же надо было придумать гения злодейской мысли, который от вида крови начинает отворачиваться и выплескивать назад все содержимое своего желудка! Из второстепенного состава также выделю Марка Стронга, сыгравшего Мерлина, специалиста по технической поддержке.\r\n\r\n«Kingsman» - это на самом деле отличное пародийное шпионское кино, которое хорошо развлекает и удерживает твое внимание до самого конца. Все-таки Мэттью Вон проделал работу, как следует и когда ты смотришь его фильм, то все остальные истории про шпионов как-то меркнут сами собой.",
            "author": "sav753ya"
        }
    ],
    "total": 316,
    "limit": 1,
    "page": 1,
    "pages": 316
}



