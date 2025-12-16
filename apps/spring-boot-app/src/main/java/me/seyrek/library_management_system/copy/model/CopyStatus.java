package me.seyrek.library_management_system.copy.model;

public enum CopyStatus {
    AVAILABLE,
    LOANED,
    MAINTENANCE,
    LOST,
    RETIRED
}

// available -> loaned
// available -> maintenance
// available -> lost
// available -> retired
// loaned -> available !!! but not is CopyService !!!
// loaned -> lost !!! but not is CopyService !!!
// loaned -> maintenance !!! but not is CopyService !!!
// maintenance -> available
// maintenance -> retired
// lost -> available
// lost -> retired