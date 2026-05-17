# Selenium Assignment

For the full assignment description, task list, and deadlines, see the course materials.

Fill in `points.yml` to track your progress. The CI will calculate your grade on every push.

Running the tests:

docker-compose up -d 

docker run --rm -v "${PWD}:/home/gradle/project" -w /home/gradle/project gradle gradle clean test -Denv=docker