# Путь к исполняемому файлу
EXECUTABLE_PATH = ./build/install/app/bin/app

.PHONY: run-dist
run-dist:
	./build/install/app/bin/app -h

.PHONY: run-dist-fp
run-dist-fr:
	./build/install/app/bin/app  -f plain src/test/resources/fixtures/file1.json src/test/resources/fixtures/file2.json

.PHONY: build
build:
	./gradlew clean build

.PHONY: run-test
run-test:
	./gradlew test

.PHONY: run-chekstyleTest
run-chekstyleTest:
	./gradlew checkstyleTest

.PHONY: build-dist
build-dist:
	./gradlew installDist

.PHONY: checkstyle
checkstyle:
	./gradlew checkstyleMain