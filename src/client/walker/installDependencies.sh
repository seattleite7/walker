#!/bin/bash
rm -rf node_modules
rm -rf app/client/bower_components
rm -rf app/client/node_modules
npm install
bower install --allow-root

cd app/client
npm install
cd ../..
