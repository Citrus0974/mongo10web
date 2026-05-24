# Настройки
$targetDir = "C:\Users\Citrus_0974_ThinkPad\Documents\sstu\DB_mongo\Projects\mongo10web"  # Путь к исходной папке
$extension = "*.java"                # Расширение файлов (например, *.txt или *.log)
$outputFile = "C:\Users\Citrus_0974_ThinkPad\Documents\sstu\DB_mongo\Projects\mongo10web\new.txt" # Путь к итоговому файлу

# Очищаем выходной файл, если он уже существует
if (Test-Path $outputFile) {
    Remove-Item $outputFile
}

# Находим все файлы и объединяем их содержимое
Get-ChildItem -Path $targetDir -Filter $extension -Recurse | ForEach-Object {
    # Получаем содержимое файла и добавляем пустую строку в конец
    Get-Content $_.FullName | Out-File -FilePath $outputFile -Append -Encoding UTF8
    "`n" | Out-File -FilePath $outputFile -Append -Encoding UTF8
}

Write-Host "Готово! Содержимое объединено в файл: $outputFile" -ForegroundColor Green
