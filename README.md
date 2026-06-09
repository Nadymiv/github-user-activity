# GitHub Activity CLI

Простий консольний застосунок на **Java 11+**, який отримує останню активність GitHub-користувача через GitHub Events API.

Без сторонніх бібліотек — використовується лише стандартна бібліотека Java.

## Можливості

- Отримання останніх подій користувача GitHub
- Підтримка основних типів подій:
  - PushEvent
  - WatchEvent
  - CreateEvent
  - IssuesEvent

- Зрозумілий вивід у консоль
- Обробка помилок API та неіснуючих користувачів

## Вимоги

- JDK 11+

## Встановлення

```bash
git clone git@github.com:Nadymiv/github-activity-cli.git
cd github-activity-cli
```

## Компіляція

```bash
javac src/App.java -d out
```

## Запуск

```bash
java -cp out App <github-username>
```

Приклад:

```bash
java -cp out App kamranahmedse
```

## Приклад результату

```text
Pushed 3 commit(s) to kamranahmedse/developer-roadmap
Starred kamranahmedse/developer-roadmap
Opened an issue in kamranahmedse/developer-roadmap
Created a repository in kamranahmedse/new-repo
```
