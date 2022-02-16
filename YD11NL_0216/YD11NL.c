#include <stdio.h>
#include <stdlib.h>

int main()
{
    FILE *fp=fopen("palencsar.txt", "wt");
    fprintf(fp, "Nev: Palencsar Eniko\nNeptun: YD11NL\nSzak: Mernokinformatikus\nKepzes: nappali\nSzint: Bsc");
    fclose(fp);
    return 0;
}
