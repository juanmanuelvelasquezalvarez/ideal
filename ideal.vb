'If the year has or not 1 week more
Function days(y As Long) As Integer
    If y = Round(Round(y * 1.242189 / 7) * 7 / 1.242189) Then
        days = 371
    Else
        days = 364
    End If
End Function
'Gregorian to ideal yyyyddd
Function ideal(f As Long) As Long
    Dim n, d As Integer
    Dim y As Long
    'Days since the glorious change of millennium
    d = f - 36520
    y = 2000 'Year initiating in 2000
    '36526 days from 1900 to 2000 in Excel
    If f > 36526 Then
        n = days(y)
        While d >= n
            d = d - n
            y = y + 1
            n = days(y)
        Wend
    Else
        While d < 0
            y = y - 1
            d = d + days(y)
        Wend
    End If
    'Day starts at 0 internally and here 1 is added
    ideal = y * 1000 + d + 1
End Function
'Ideal to gregorian. Year and day
Function gregorian(d As Integer, y As Long) As Long
    'The Gregorian 1/1/2000 would be 7/1/2000 in the ideal calendar, a standard I propose.
    'Day of the year counting since 0 (Sunday). 6 is Saturday, day that the change of millennium was.
    gregorian = 36519 + d
    Dim i As Long
    i = 2000
    If y > 2000 Then
        While i < y
            gregorian = gregorian + days(i)
            i = i + 1
        Wend
    End If
    If y < 2000 Then
        While i > y
            i = i - 1
            gregorian = gregorian - days(i)
        Wend
    End If
End Function
'Parameter day of the year
Function weekideal(d As Integer) As Integer
    weekideal = ((d - 1) / 7) + 1
End Function
Function monthideal(d As Integer) As Integer
    monthideal = ((d - 1) / 28) + 1
End Function
'Day of week and of month
Function dwideal(d As Integer) As Integer
    dwideal = ((d - 1) Mod 7) + 1
End Function
Function dmideal(d As Integer) As Integer
    dmideal = ((d - 1) Mod 28) + 1
End Function
