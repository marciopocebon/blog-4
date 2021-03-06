```
                                  ___           ___                    ___           ___               
     _____                       /\  \         /\__\                  /\  \         /\  \              
    /::\  \                     /::\  \       /:/ _/_                /::\  \       /::\  \     ___     
   /:/\:\  \                   /:/\:\  \     /:/ /\  \              /:/\:\  \     /:/\:\__\   /\__\    
  /:/ /::\__\   ___     ___   /:/  \:\  \   /:/ /::\  \            /:/ /::\  \   /:/ /:/  /  /:/__/    
 /:/_/:/\:|__| /\  \   /\__\ /:/__/ \:\__\ /:/__\/\:\__\          /:/_/:/\:\__\ /:/_/:/  /  /::\  \    
 \:\/:/ /:/  / \:\  \ /:/  / \:\  \ /:/  / \:\  \ /:/  /          \:\/:/  \/__/ \:\/:/  /   \/\:\  \__ 
  \::/_/:/  /   \:\  /:/  /   \:\  /:/  /   \:\  /:/  /            \::/__/       \::/__/     ~~\:\/\__\
   \:\/:/  /     \:\/:/  /     \:\/:/  /     \:\/:/  /              \:\  \        \:\  \        \::/  /
    \::/  /       \::/  /       \::/  /       \::/  /                \:\__\        \:\__\       /:/  / 
     \/__/         \/__/         \/__/         \/__/                  \/__/         \/__/       \/__/  
```
[![CircleCI](https://circleci.com/gh/marioalvial/blog.svg?style=svg)](https://circleci.com/gh/marioalvial/blog)
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/8b8e69030a1787869027)

Blog API

## Documentation

[Postman](https://documenter.getpostman.com/view/2673922/RznJmbYT)

## Requirements

For building and running the application you need:

- [Gradle](https://gradle.org/)
- [Docker](https://www.docker.com/)

## Running

First, clone the project:

```shell
git clone https://github.com/marioalvial/blog.git
cd blog
```

#### Running with docker

```shell
./gradlew build && docker-compose up --build
```

## Continuous Integration
Continuous Integration is configured on CircleCI. Checkout the [continuous integration here](https://circleci.com/gh/marioalvial/blog)

##  Testing

```shell
./gradlew test
```

## Built With

- [Kotlin](https://kotlinlang.org/) - Programming language
- [IntelliJ](https://www.jetbrains.com/idea/) - IDE
- [Spring](https://spring.io/) - Java Framework
- [Gradle](https://gradle.org/) - Dependency Management
- [Docker](https://www.docker.com/) - Containerization Platform
