#!/bin/bash

cp resources/git/.gitmodules .gitmodules
git submodule update --recursive
