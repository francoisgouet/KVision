
:: j'avais oublie le diese pour commenter,a priori ecrit les lignes
:: for /F "delims=" %i in (domaines.txt) echo "%i"
:: for /F "delims=" %i in (domaines.txt) do echo %i
REM @echo off est important sinon ça prend en compte la sortie de l'interpreteur au lieu de l'interieur du fhiciers
@echo off 
::for /f "delims= " %%i in (domaines.txt) do echo %%i
::FOR /f "delims= " %%i in (domaines.txt) do @echo %%i
::FOR /F "delims=" i in (domaines.txt) do echo %i
::FOR /F "delims=" %%i in (domaines.txt) do echo %%i
:: ok donc délimiter => logique mais quelle con
:: apparement reg 
:: donc reviser reg
:: bach serie de commandes cmd
::FOR /F "delims=" %%i in (domaines.txt) do echo 	expr match %%i 'abc[A-Z]*.2'
:: je dois encore beaucoup apprendre en bash ça me coute mais je continue quand même
:: j'ai du mal avec echo et avec les variable et les boucles en fait je connais pas grand chose
::FOR /F "delims=" %%i in (domaines.txt) do if [[ %%i =~ ^[0-9] ]]; 
::then
::   echo "La chaîne commence par un chiffre"
::else
::    echo "La chaîne ne commence pas par un chiffre"
::fi
:: comprendre pourquoi je dois le lancer depuis cmd et que ca a pas l'air de marcher depuis autre
::FOR /F "delims=" %%i in (domaines.txt) do echo %%i
::Dans notre exemple, nous affichons les lignes 10 à 20.

::for /f "tokens=1,* delims=:" %%i in ('findstr /n /r . domaines.txt') do if %%i geq 10 if %%i leq 20 echo %%j 
::for /f "tokens=1,* delims=:" %%i in (domaines.txt) do if %%i geq 10 if %%i leq 20 echo %%j 
::for /f "tokens=1,* delims=:" %%i in (domaines.txt) do if %%i geq 10 echo %%i
:: cette ligne fonctionne et je comprend for /f "delims=" %%i in ('type "domaines.txt" ^|findstr /i /r "Agro"') do echo %%i
::for /f "delims=" %%i in (domaines.txt) do (if %%i==Sciences maritimes" echo %%i)

::for /f "delims=" %%i in (domaines.txt) do (if %%i | grep -qE '^[0-9] echo %%i)
::for /f "delims=" %%i in (domaines.txt) do (if 1 == 0 echo %%i)

::for /f "delims=" %%i in (domaines.txt) do ("${ma_chaine%%[!0-9]*}"  echo %%i)
::for /f "delims=" %%i in (domaines.txt) do $(set m = %%i echo %%m)
pause