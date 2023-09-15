#include <iostream>
#include <cmath>

using namespace std;
const int N = 20;

int color;
int rows, cols;
int result;
int a[N][N];
int v[N][N];

int hori(int x, int y, int k)
{
    // 到边界直接返回
    if (y == cols)
    {
        return k * 10;
    }
    // 颜色不能连续了
    if (a[x][y] != color)
    {
        if (a[x][y] == 0)
        {
            v[x][y] = max(v[x][y], k * 10);
        }
        return k * 10;
    }

    return hori(x, y + 1, k + 1);
}

int verti(int x, int y, int k)
{
    if (x == rows)
    {
        return k * 10;
    }

    // 颜色不能连续了
    if (a[x][y] != color)
    {
        if (a[x][y] == 0)
        {
            v[x][y] = max(v[x][y], k * 10);
        }
        return k * 10;
    }

    return verti(x + 1, y, k + 1);
}

int upri(int x, int y, int k)
{
    if (x == -1 || y == cols)
    {
        return k * 10;
    }

    // 颜色不能连续了
    if (a[x][y] != color)
    {
        if (a[x][y] == 0)
        {
            v[x][y] = max(v[x][y], k * 10);
        }
        return k * 10;
    }

    return upri(x - 1, y + 1, k + 1);
}

int lori(int x, int y, int k)
{
    if (x == rows || y == cols)
    {
        return k * 10;
    }

    // 颜色不能连续了
    if (a[x][y] != color)
    {
        if (a[x][y] == 0)
        {
            v[x][y] = max(v[x][y], k * 10);
        }
        return k * 10;
    }

    return lori(x + 1, y + 1, k + 1);
}

int main()
{
    cin >> color;
    cin >> rows >> cols;
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            cin >> a[i][j];

    // 初始化
    for (int i = 0; i < rows; i++)
        for (int j = 0; j < cols; j++)
            if (a[i][j] == 0)
                v[i][j] = 10;

    if (a[7][7] == 0) v[7][7] = 12;
    if(a[7][8] == 0) v[7][8] = 12;
    

    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            if (a[i][j] == color)
            {
                // 水平
                int t = hori(i, j, 1);
                if (j >= 0 && a[i][j - 1] == 0)
                {
                    v[i][j - 1] = max(v[i][j - 1], t);
                }
                // 竖直
                t = verti(i, j, 1);
                if (i >= 0 && a[i - 1][j] == 0)
                {
                    v[i - 1][j] = max(v[i - 1][j], t);
                }

                // 右上
                t = upri(i, j, 1);
                if (i + 1 < rows && j - 1 >= 0 && a[i + 1][j - 1] == 0)
                {
                    v[i + 1][j - 1] = max(v[i + 1][j - 1], t);
                }

                // 右下
                t = lori(i, j, 1);
                if (i - 1 >= 0 && j - 1 >= 0 && a[i - 1][j - 1] == 0)
                {
                    v[i - 1][j - 1] = max(v[i - 1][j - 1], t);
                }
            }
        }
    }

    int tmp = 0;
    int x, y;
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            if (v[i][j] > tmp)
            {
                tmp = v[i][j];
                x = i;
                y = j;
            }
        }
    }

    printf("%d\n", x * rows + y);

    return 0;
}
