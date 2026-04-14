param(
    [string]$TomcatHome = 'C:\Users\Mega-PC\Downloads\apache-tomcat-10.1.53\apache-tomcat-10.1.53'
)

$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$projectRoot = Split-Path -Parent $scriptDir
$mavenWrapper = Join-Path $projectRoot 'mvnw.cmd'
$warSource = Join-Path $projectRoot 'target\ROOT.war'
$webappsDir = Join-Path $TomcatHome 'webapps'
$dbDir = Join-Path $TomcatHome 'data'
$warTarget = Join-Path $webappsDir 'ROOT.war'
$explodedTarget = Join-Path $webappsDir 'ROOT'
$startup = Join-Path $TomcatHome 'bin\startup.bat'
$shutdown = Join-Path $TomcatHome 'bin\shutdown.bat'

if (-not (Test-Path $mavenWrapper)) {
    throw "mvnw.cmd not found at $mavenWrapper"
}
if (-not (Test-Path $startup)) {
    throw "startup.bat not found at $startup"
}
if (-not (Test-Path $webappsDir)) {
    throw "webapps folder not found at $webappsDir"
}

Write-Host '1/4 Building project (clean package)...'
Push-Location $projectRoot
try {
    & $mavenWrapper clean package
    if ($LASTEXITCODE -ne 0) {
        throw "Maven build failed with exit code $LASTEXITCODE"
    }
}
finally {
    Pop-Location
}

if (-not (Test-Path $warSource)) {
    throw "WAR file not found after build: $warSource"
}

$env:CATALINA_HOME = $TomcatHome
$env:CATALINA_BASE = $TomcatHome
if (-not (Test-Path $dbDir)) {
    New-Item -ItemType Directory -Path $dbDir | Out-Null
}
$dbPath = (Join-Path $dbDir 'eventsdb').Replace('\\', '/')
$env:APP_DB_URL = "jdbc:h2:file:$dbPath"

if (Test-Path $shutdown) {
    Write-Host '2/4 Stopping Tomcat (if running)...'
    & $shutdown | Out-Null
    Start-Sleep -Seconds 3
}

Write-Host '3/4 Deploying ROOT.war...'
if (Test-Path $warTarget) {
    Remove-Item $warTarget -Force
}
if (Test-Path $explodedTarget) {
    Remove-Item $explodedTarget -Recurse -Force
}
Copy-Item $warSource $warTarget -Force

Write-Host '4/4 Starting Tomcat...'
& $startup

Write-Host 'Done. Open http://localhost:8080/'

