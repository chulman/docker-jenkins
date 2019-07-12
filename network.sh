#!/usr/bin/env bash


docker network create --driver bridge jenkins-net
# docker network rm jenkins-net
# docker network ls