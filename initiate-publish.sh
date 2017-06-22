#!/bin/bash
# This script initiates the Gradle publishing task when pushes to master occur.
# NOTE: Travis-CI can only publish SNAPSHOT versions. To release a version, you need
#       to use the internal ReadyTalk Jenkins job.

if [ "$TRAVIS_REPO_SLUG" == "ReadyTalk/swt-bling" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then
  if [[ $(./gradlew -q getVersion) != *SNAPSHOT* ]]; then
      echo 'Travis can only publish snapshots. To publish a release, use the ReadyTalk Jenkins instance.'
      return 0
  fi

  echo -e "Starting publish to Sonatype...\n"

  ./gradlew uploadArchives -PnexusUsername="${NEXUS_USERNAME}" -PnexusPassword="${NEXUS_PASSWORD}"
  RETVAL=$?

  if [ $RETVAL -eq 0 ]; then
    echo 'Completed publish!'
  else
    echo 'Publish failed.'
    return 1
  fi

fi
