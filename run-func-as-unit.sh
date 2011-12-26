mv -v test/unit test/unit-bkp
mv -v test/functional test/unit
grails test-app $1 -unit -echoOut
mv -v test/unit test/functional
mv -v test/unit-bkp test/unit
