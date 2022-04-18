package capstone.luis.devbyte.DTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Pagination {
    @Min(0)
    private int page = 0;
    @Min(0)
    @Max(100)
    private int count = 20;

    public Pagination() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
