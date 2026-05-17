# Selenium Assignment

For the full assignment description, task list, and deadlines, see the course materials.

Fill in `points.yml` to track your progress. The CI will calculate your grade on every push.

IMPORTANT: During SearchandFilter test a Cloudflare popup appears. There's 10 seconds to accept it and also for it to process, but it was plenty for me. If you doesn't accept it, it will fail all the test cases.   

Running the tests:

docker-compose up -d 

docker run --rm -v "${PWD}:/home/gradle/project" -w /home/gradle/project gradle gradle clean test -Denv=docker
