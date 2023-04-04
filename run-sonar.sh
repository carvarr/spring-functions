#!/usr/bin/env bash
./gradlew sonarqube \
  -Dsonar.projectKey=key \
  -Dsonar.organization=org \
  -Dsonar.host.url=https://url.sonar \
  -Dsonar.login=token