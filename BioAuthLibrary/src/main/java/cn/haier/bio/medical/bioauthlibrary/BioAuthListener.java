package cn.haier.bio.medical.bioauthlibrary;

public interface BioAuthListener {
    void authSuccess(String response);
    void authFailure(String msg);
    void authError(String msg);
}
