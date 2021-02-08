*** Settings ***
Library ru.stqa.mfp.addressbook.rf.AddressbookKeywords
Suit Setup    Init Application Manager
Suit Teardown    Stop Application Manager


*** Test Cases ***
Can Create Group With Valid Data
    ${old_count} =    Get Group count
    Create Group    test name    test header    test footer
    ${new_count} =    Get Group count
    ${expected_count} =    Evaluate
    Should Be Equal As Integer    $(new_count)    $(expected_count)