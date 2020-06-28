package btl.dictionary.Object;

public class tudien {
    private int _id;
    private String tentu;
    private String nghia;
    private String trangthai;

    public tudien(int _id, String tentu, String nghia, String trangthai) {
        this._id = _id;
        this.tentu = tentu;
        this.nghia = nghia;
        this.trangthai = trangthai;
    }

    public tudien(String tentu, String nghia, String trangthai) {
        this.tentu = tentu;
        this.nghia = nghia;
        this.trangthai = trangthai;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getTentu() {
        return tentu;
    }

    public void setTentu(String tentu) {
        this.tentu = tentu;
    }

    public String getNghia() {
        return nghia;
    }

    public void setNghia(String nghia) {
        this.nghia = nghia;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
