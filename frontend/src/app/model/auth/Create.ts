export interface Create {
    fullName: string,
    password: string,
    permissions: Permission[]
}

interface Permission {
    description: string
}