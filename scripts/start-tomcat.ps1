param(
    [string]$TomcatHome = 'C:\Users\Mega-PC\Downloads\apache-tomcat-10.1.53\apache-tomcat-10.1.53'
)

$ErrorActionPreference = 'Stop'

if (-not (Test-Path $TomcatHome)) {
    throw "Tomcat folder not found: $TomcatHome"
}

$env:CATALINA_HOME = $TomcatHome
$env:CATALINA_BASE = $TomcatHome

$startup = Join-Path $TomcatHome 'bin\startup.bat'
if (-not (Test-Path $startup)) {
    throw "startup.bat not found: $startup"
}

Write-Host "Starting Tomcat from $TomcatHome"
& $startup

